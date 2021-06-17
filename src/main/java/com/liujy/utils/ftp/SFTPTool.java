package com.liujy.utils.ftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

/**
 * sftp 工具类
 * 
 *  
 *
 */
public class SFTPTool {
    private ChannelSftp sftp = null;
    private String ip;
    private String port;
    private String userName;
    private String password;

    public static SFTPTool getNewInstance() {
        return new SFTPTool();
    }

    /**
     * 连接sftp服务器
     * 
     * @param sftpip
     *            ip地址
     * @param sftpport
     *            端口
     * @param sftpusername
     *            用户名
     * @param sftppassword
     *            密码
     * @return channelSftp
     * @throws SPMException
     */
    public ChannelSftp connect(String sftpip, int sftpport, String sftpusername, String sftppassword) throws JSchException {
        sftp = new ChannelSftp();
        try {
            JSch jsch = new JSch();
            jsch.getSession(sftpusername, sftpip, sftpport);
            Session sshSession = jsch.getSession(sftpusername, sftpip, sftpport);
            sshSession.setPassword(sftppassword);
            Properties sshConfig = new Properties();
            sshConfig.put("StrictHostKeyChecking", "no");
            sshSession.setConfig(sshConfig);
            // 设置超时时间为
            // sshSession.setTimeout();
            sshSession.connect();
            Channel channel = sshSession.openChannel("sftp");
            channel.connect();
            sftp = (ChannelSftp) channel;
        } catch (JSchException e) {
            throw e;
        }
        return sftp;
    }

    /**
     * 打开指定目录
     * 
     * @param directory
     *            directory
     * @return 是否打开目录
     */
    public boolean openDir(String directory) {
        try {
            sftp.cd(directory);
            return true;
        } catch (SftpException e) {
            return false;
        }
    }

    /**
     * 下载文件
     * 
     * @param ftpDir
     *            存放下载文件的SFTP路径
     * @param locDir
     *            下载的文件 SFTP上的文件名称
     * @param ftpFileName
     *            FTP上的文件名称
     * @param deleteFtpFile
     *            the delete ftp file
     * @return 本地文件对象
     * @throws FileNotFoundException
     *             FileNotFoundException
     */
    public File download(String ftpDir, String locDir, String ftpFileName, boolean deleteFtpFile) throws FileNotFoundException, SftpException, IOException {

        File file = null;
        FileOutputStream output = null;
        try {
            String now = sftp.pwd();
            sftp.cd(ftpDir);
            file = new File(locDir + File.separator + ftpFileName);
            output = new FileOutputStream(file);
            sftp.get(ftpFileName, output);
            sftp.cd(now);
            if (deleteFtpFile) {
                sftp.rm(ftpFileName);
            }
        } catch (FileNotFoundException e) {
            throw e;
        } catch (SftpException e) {
            throw e;
        } finally {
            if (null != output) {
                try {
                    output.close();
                } catch (IOException e) {
                    throw e;
                }
            }
        }
        return file;
    }

    /**
     * 上传文件
     * 
     * @param directory
     *            上传的目录
     * @param file
     *            要上传的文件 parentDir 上传目录的上级目录
     * @return 是否上传
     */
    public boolean uploadFile(String directory, File file) throws SftpException, IOException {
        boolean flag = false;
        FileInputStream in = null;
        try {
            String now = sftp.pwd();
            sftp.cd(directory);
            in = new FileInputStream(file);
            sftp.put(in, file.getName());
            sftp.cd(now);
            if (file.exists()) {
                flag = true;
            } else {
                flag = false;
            }
        } catch (SftpException e) {
            throw e;
        } finally {
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    throw e;
                }
            }
        }
        return flag;
    }

    /**
     * 删除文件
     * 
     * @param directory
     *            要删除文件所在目录
     * @param deleteFile
     *            要删除的文件
     * @param sftp
     * @throws SftpException
     */
    public void delete(String directory, String deleteFile) throws SftpException {
        try {
            String now = sftp.pwd();
            sftp.cd(directory);
            sftp.rm(deleteFile);
            sftp.cd(now);
        } catch (SftpException e) {
            throw e;
        }
    }

    /**
     * 列出目录下的文件
     * 
     * @param directory
     *            要列出的目录
     * @param regex
     *            指定文件名的格式
     * @param needFullMatch
     *            是否要求全局匹配(true:是;false:否)
     * @return 文件列表
     * @throws SftpException
     *             SftpException
     * @throws SPMException
     *             SPMException
     */
    public List<String> listFiles(String directory, String regex) throws SftpException {
        List<String> ftpFileNameList = new ArrayList<String>();
        @SuppressWarnings("unchecked")
        Vector<LsEntry> sftpFile = sftp.ls(directory);
        LsEntry isEntity = null;
        String fileName = null;
        Iterator<LsEntry> sftpFileNames = sftpFile.iterator();
        while (sftpFileNames.hasNext()) {
            isEntity = (LsEntry) sftpFileNames.next();
            fileName = isEntity.getFilename();

            if (StringUtils.isEmpty(regex)) {
                ftpFileNameList.add(fileName);
            } else {
                if (fileName.matches(regex)) {
                    ftpFileNameList.add(fileName);
                }
            }
        }
        return ftpFileNameList;
    }

    /**
     * 断开FTP连接
     * 
     * @throws JSchException
     */
    public void disconnect() throws JSchException {
        if (null != sftp) {
            sftp.disconnect();
            if (null != sftp.getSession()) {
                sftp.getSession().disconnect();
            }
        }
    }

    public ChannelSftp getSftp() {
        return sftp;
    }

    public void setSftp(ChannelSftp sftp) {
        this.sftp = sftp;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}

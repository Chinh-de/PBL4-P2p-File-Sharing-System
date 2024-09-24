create database P2PFileSharingSystem
use P2PFileSharingSystem;

-- Create table 'peers'
CREATE TABLE peers (
    username VARCHAR(255) PRIMARY KEY,
    hashPassword VARCHAR(255) NOT NULL,
    ip VARCHAR(255),
    currentDownloads INT DEFAULT 0,
    currentUploads INT DEFAULT 0
);

-- Create table 'files'
CREATE TABLE files (
    id INT AUTO_INCREMENT PRIMARY KEY,
    size BIGINT NOT NULL,
    describe VARCHAR(255) NOT NULL
);

-- Create table 'file_peer' to map files to peers
CREATE TABLE file_peer (
    fileid INT,
    username VARCHAR(255),
    path VARCHAR(255),
    FOREIGN KEY (fileid) REFERENCES files(id),
    FOREIGN KEY (username) REFERENCES peers(username),
    PRIMARY KEY (fileid, username)
);

-- Create table 'logs'
CREATE TABLE logs (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255),
    action INT NOT NULL,
    time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (username) REFERENCES peers(username)
);


create database P2PFileSharingSystem
use P2PFileSharingSystem;

-- Tạo bảng `peer`
CREATE TABLE peer (
    username VARCHAR(255) PRIMARY KEY,
    hashPassword VARCHAR(255) NOT NULL
);

-- Tạo bảng `file`
CREATE TABLE `file` (
    id INT AUTO_INCREMENT PRIMARY KEY,
    `name` NVARCHAR(255),
    size BIGINT NOT NULL
);

-- Tạo bảng `file_peer`
CREATE TABLE file_peer (
    fileid INT,
    peerUsername VARCHAR(255),
    `path` NVARCHAR(255),
    PRIMARY KEY (fileid, peerUsername),
    FOREIGN KEY (fileid) REFERENCES file(id) ON DELETE CASCADE,
    FOREIGN KEY (peerUsername) REFERENCES peer(username) ON DELETE CASCADE
);

-- Tạo bảng `log`
CREATE TABLE log (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255),
    `action` INT NOT NULL,
    `time` TIMESTAMP NOT NULL,
    FOREIGN KEY (username) REFERENCES peer(username) ON DELETE CASCADE
);



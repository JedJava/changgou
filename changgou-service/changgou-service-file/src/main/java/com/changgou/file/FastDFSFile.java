package com.changgou.file;

import lombok.Data;

import java.io.Serializable;

@Data
public class FastDFSFile implements Serializable {

    private String name;    // 文件名称
    private byte[] content; // 文件内容
    private String ext;     // 文件扩展名
    private String md5;     // 文件摘要
    private String author;  // 文件作者

    public FastDFSFile(String name, byte[] content, String ext, String md5, String author) {
        this.name = name;
        this.content = content;
        this.ext = ext;
        this.md5 = md5;
        this.author = author;
    }

    public FastDFSFile(String name, byte[] content, String ext) {
        this.name = name;
        this.content = content;
        this.ext = ext;
    }

}

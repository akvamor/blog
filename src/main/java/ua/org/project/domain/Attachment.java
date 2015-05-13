package ua.org.project.domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Dmitry Petrov on 5/28/14.
 */
@MappedSuperclass
public class Attachment implements Serializable {
    
	private static final long serialVersionUID = -1459457818226198996L;
	
	private Long id;
    private String fileName;
    private String contentType;
    private byte[] fileData;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "FILE_NAME")
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Column(name = "CONTENT_TYPE")
    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    @Column(name = "FILE_DATA")
    @Lob @Basic(fetch = FetchType.LAZY)
    public byte[] getFileData() {
        return fileData;
    }

    public void setFileData(byte[] fileData) {
        this.fileData = fileData;
    }
}

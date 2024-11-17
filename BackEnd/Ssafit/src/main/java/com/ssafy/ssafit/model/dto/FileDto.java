package com.ssafy.ssafit.model.dto;

public class FileDto {
	private int fileId;
	private String fileName;
	private String fileCase;
	private String extension;
	private int userId;
	private int questionId;

	public int getFileId() {
		return fileId;
	}

	public void setFileId(int fileId) {
		this.fileId = fileId;
	}

	public FileDto(int fileId, String fileName, String fileCase, String extension, int userId, int questionId) {
		super();
		this.fileId = fileId;
		this.fileName = fileName;
		this.fileCase = fileCase;
		this.extension = extension;
		this.userId = userId;
		this.questionId = questionId;
	}

	public FileDto() {
		// TODO Auto-generated constructor stub
	}

	public FileDto(String fileName, String fileCase, String extension, int userId, int questionId) {
		super();
		this.fileName = fileName;
		this.fileCase = fileCase;
		this.extension = extension;
		this.userId = userId;
		this.questionId = questionId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileCase() {
		return fileCase;
	}

	public void setFileCase(String fileCase) {
		this.fileCase = fileCase;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getQuestionId() {
		return questionId;
	}

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}

}

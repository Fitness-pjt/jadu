package com.ssafy.ssafit.service.file;

import java.io.IOException;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.ssafit.model.dao.FileDao;
import com.ssafy.ssafit.model.dto.FileDto;
import com.ssafy.ssafit.model.dto.User;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service
public class FileServiceImpl implements FileService {
	private FileDao fileDao;
	private final S3Client s3Client;
	private final String BUCKET_NAME = "attnnskybucket";

	public FileServiceImpl(S3Client s3Client, FileDao fileDao) {
		this.s3Client = s3Client;
		this.fileDao = fileDao;
	}

	@Override
	public String upload(MultipartFile file, User user, FileDto fileDto) {

		String fileName = file.getOriginalFilename();
		String nameWithoutExtension = fileName.substring(0, fileName.lastIndexOf("."));
		String extension = fileName.substring(fileName.lastIndexOf(".") + 1);

		fileName = user.getUserNickname() + nameWithoutExtension + UUID.randomUUID().toString();

		fileDto.setFileName(fileName);
		fileDto.setExtension(extension);
		fileDto.setUserId(user.getUserId());
		// 이부분 고민 필요

		try

		{
			PutObjectRequest putObjectRequest = PutObjectRequest.builder().bucket(BUCKET_NAME).key(fileName)
					.contentType("image/jpeg").build();

			s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

			String fileUrl = getFileUrl(fileName);

			fileDao.addFile(fileDto);
//			System.out.println(fileUrl);
			return fileUrl; // URL 반환

		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String getFile(User user, String fileCase) {

//		fileDao.getFile(user.getUserId(), fileCase);

		return null;
	}

	public String getFileUrl(String fileName) {
		return String.format("https://%s.s3.%s.amazonaws.com/%s", BUCKET_NAME, "ap-northeast-2", fileName);
	}

	// 파일테이블에 questionId 업로드 시 사용 
	@Override
	public void updateFileName(int questionId, String questionFileName) {
		fileDao.updateFile(questionId, questionFileName);
	}

}

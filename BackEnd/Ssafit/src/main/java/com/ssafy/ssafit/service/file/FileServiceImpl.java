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
	public boolean upload(MultipartFile file, User user, FileDto fileDto) {

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

			fileDao.addFile(fileDto);
			return true;
		} catch (IOException e) {
			return false;
		}

	}

	@Override
	public String getFile(User user, String fileCase) {

//		fileDao.getFile(user.getUserId(), fileCase);

		return null;
	}

}

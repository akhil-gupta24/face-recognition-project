package com.amazonaws.samples;

import java.io.File;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

public class SendingImage {

//	public static void main(String[] args) {
//		uploadingImage("Image_0");
//	}

	public static void uploadingImage(String imageName) {
		// extracting image name from string imageName
		String[] val = imageName.split(" ");
		// System.out.println(val);
		String imageFileName = val[0];
		// System.out.println(imageFileName);
		Regions clientRegion = Regions.DEFAULT_REGION;
		String bucketName = "real-time-face-data-collector-bucket";
		// String stringObjKeyName = "awsimage.png";
		String fileObjKeyName = imageFileName + ".jpg";
		String fileName = "C:\\Users\\DA1041TU\\git\\facerecognitionfinalyear\\Face Recognition Door Opening\\"
				+ imageFileName + ".jpg";

		try {
			// This code expects that you have AWS credentials set up per:
			// https://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/setup-credentials.html
			AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withRegion(clientRegion).build();

			// Upload a text string as a new object.
			// s3Client.putObject(bucketName, stringObjKeyName, "Uploaded String Object");

			// Upload a file as a new object with ContentType and title specified.
			PutObjectRequest request = new PutObjectRequest(bucketName, fileObjKeyName, new File(fileName));
			ObjectMetadata metadata = new ObjectMetadata();
			metadata.setContentType("plain/text");
			metadata.addUserMetadata("title", "someTitle");
			request.setMetadata(metadata);
			s3Client.putObject(request);
		} catch (AmazonServiceException e) {
			// The call was transmitted successfully, but Amazon S3 couldn't process
			// it, so it returned an error response.
			e.printStackTrace();
		} catch (SdkClientException e) {
			// Amazon S3 couldn't be contacted for a response, or the client
			// couldn't parse the response from Amazon S3.
			e.printStackTrace();
		}

	}
	// public static void main(String[] args) throws IOException { }
}

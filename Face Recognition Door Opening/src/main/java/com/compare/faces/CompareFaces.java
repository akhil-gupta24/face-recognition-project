package com.compare.faces;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.services.rekognition.model.AmazonRekognitionException;
import com.amazonaws.services.rekognition.model.CompareFacesMatch;
import com.amazonaws.services.rekognition.model.CompareFacesRequest;
import com.amazonaws.services.rekognition.model.CompareFacesResult;
import com.amazonaws.services.rekognition.model.Image;
import com.amazonaws.services.rekognition.model.S3Object;
import java.util.List;

public class CompareFaces {

	public static boolean faceMatched(String sourceImage, String targetImage) throws Exception {
		System.out.println("SourceImage is " + sourceImage);
		System.out.println("TargetImage is " + targetImage);
//		String photo1 = "image2.jpg";
//		String photo2 = "image4.jpg";
		String sourceBucketName = "real-time-face-data-collector-bucket";
		String targetBucketName = "face-database-collection-bucket";

		AmazonRekognition rekognitionClient = AmazonRekognitionClientBuilder.standard()
				.withRegion(Regions.DEFAULT_REGION).build();
		Image source = new Image()
				.withS3Object(new S3Object().withName(sourceImage + ".jpg").withBucket(sourceBucketName));
		Image target = new Image().withS3Object(new S3Object().withName(targetImage).withBucket(targetBucketName));
		CompareFacesRequest compareFacesRequest = new CompareFacesRequest().withSourceImage(source)
				.withTargetImage(target).withSimilarityThreshold(80F);

		try {

			CompareFacesResult result = rekognitionClient.compareFaces(compareFacesRequest);
			List<CompareFacesMatch> lists = result.getFaceMatches();

			System.out.println("Detected labels for " + sourceImage + " and " + targetImage + "are");

			if (!lists.isEmpty()) {
				for (CompareFacesMatch label : lists) {
					// printing result of matched data
					System.out.println(label.getFace() + ": Similarity is " + label.getSimilarity().toString());
					if (label.getSimilarity() > 50) {
						System.out.println("Faces match of both source and target images");
						return true;
					}
				}
			} else {
				System.out.println("Face does not detected");
				return false;
			}
		} catch (AmazonRekognitionException e) {
			e.printStackTrace();
		}
		System.out.println("Reach at last of comparefaces method");
		return false;
	}
}

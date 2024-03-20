//import ai.onnxruntime.OrtEnvironment;
//import ai.onnxruntime.OrtException;
//import ai.onnxruntime.OrtSession;
//import ai.onnxruntime.OrtTensor;
//import org.springframework.stereotype.Service;
//
//import java.util.Collections;
//import java.util.List;
//
//@Service
//public class OnnxModelService {
//
//    private OrtEnvironment environment;
//    private OrtSession session;
//
//    public OnnxModelService() throws OrtException {
//        // Initialize ONNX Runtime environment
//        environment = OrtEnvironment.getEnvironment();
//
//        // Load ONNX model
//        session = environment.createSession("DeepLearningModel/model.onnx", new OrtSession.SessionOptions());
//    }
//
//    public float[] predict(List<Float> inputData) throws OrtException {
//        // Prepare input tensor
//        float[] inputArray = inputData.stream().mapToFloat(Float::floatValue).toArray();
//        OrtTensor inputTensor = OrtTensor.createTensor(environment, inputArray);
//
//        // Perform inference
//        OrtTensor outputTensor = (OrtTensor) session.run(Collections.singletonMap("input", inputTensor)).get(0);
//
//        // Get prediction result
//        float[] result = (float[]) outputTensor.getValue();
//
//        // Close the session
//        outputTensor.close();
//
//        return result;
//    }
//}
package br.com.alura.screenmatch.service;

public class FetchChatGPT {

  public static String getTranslation(String text) {
    return text;
//    Dotenv dotenv = Dotenv.load();
//    String apiKey = dotenv.get("OPENAI_API_KEY");
//    OpenAiService service = new OpenAiService(apiKey);
//
//    CompletionRequest completionRequest = CompletionRequest.builder()
//      .model("gpt-3.5-turbo-instruct")
//      .prompt("translate the following text to portuguese: " + text)
//      .maxTokens(1000)
//      .temperature(0.7)
//      .build();
//
//    var response = service.createCompletion(completionRequest);
//    System.out.println(response.getChoices().getFirst().getText());
//    return response.getChoices().getFirst().getText();
  }
}

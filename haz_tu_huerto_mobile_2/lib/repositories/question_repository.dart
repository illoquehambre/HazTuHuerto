import 'dart:convert';
import 'package:get_it/get_it.dart';
import 'package:haz_tu_huerto_mobile_2/models/question/new_question_dto.dart';
import 'package:haz_tu_huerto_mobile_2/models/question/question_detail_dto.dart';
import 'package:haz_tu_huerto_mobile_2/models/question/question_response_dto.dart';
import 'package:http/http.dart';
import 'package:image_picker/image_picker.dart';
import 'package:injectable/injectable.dart';

import '../rest/rest_client.dart';

@Order(-1)
@singleton
class QuestionRepository {
  late RestAuthenticatedClient _client;

  QuestionRepository() {
    _client = GetIt.I.get<RestAuthenticatedClient>();
    //_client = RestClient();
  }

  Future<dynamic> findAll(int index, String token) async {
    String url = "/question?page=$index";

    var response = await _client.get(url, token);

    if (response is NotFoundException) {
      String aux = "No se ha encontrado ningún post en esta página de...";
      return aux;
    }

    return QuestionResponseDto.fromJson(jsonDecode(response));
  }

    Future<dynamic> create(List<XFile> files, NewQuestionDto quest) async {
    String url = "/question";

    StreamedResponse response = await _client.postMultipartChetada(url, files, quest, 'newQuest');

    var stringResponse = await response.stream.bytesToString();

    return QuestionDetailsDto.fromJson(jsonDecode(stringResponse));
  }

}

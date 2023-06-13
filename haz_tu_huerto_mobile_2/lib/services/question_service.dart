import 'package:get_it/get_it.dart';
import 'package:haz_tu_huerto_mobile_2/config/locator.dart';
import 'package:haz_tu_huerto_mobile_2/models/question/new_question_dto.dart';
import 'package:haz_tu_huerto_mobile_2/models/question/question_detail_dto.dart';
import 'package:haz_tu_huerto_mobile_2/models/question/question_response_dto.dart';
import 'package:haz_tu_huerto_mobile_2/repositories/question_repository.dart';
import 'package:haz_tu_huerto_mobile_2/services/services.dart';
import 'package:image_picker/image_picker.dart';
import 'package:injectable/injectable.dart';

@Order(2)
@singleton
class QuestionService {
  late QuestionRepository _questRepository;
  late LocalStorageService _localStorageService;

  QuestionService() {
    _questRepository = getIt<QuestionRepository>();
    GetIt.I
        .getAsync<LocalStorageService>()
        .then((value) => _localStorageService = value);
  }

  Future<dynamic> findAll(int index) async {
    String? token = await _localStorageService.getFromDisk("user_token");

    if (token != null) {
      QuestionResponseDto response =
          await _questRepository.findAll(index, token);
      return response;
    }
    throw Exception("There is an error in the service.");
  }


  Future<dynamic> findById(String id) async {
    String? token = await _localStorageService.getFromDisk("user_token");

    if (token != null) {
      QuestionDetailsDto response = await _questRepository.findById(id, token);
      return response;
    }
    throw Exception("There is an error in the service.");
  }

  Future<QuestionDetailsDto> create(
      List<XFile> file, NewQuestionDto quest) async {
    String? token = await _localStorageService.getFromDisk("user_token");

    if (token != null) {
      QuestionDetailsDto response = await _questRepository.create(file, quest);
      return response;
    }
    throw new Exception("Ha ocurrido un error en el servicio");
  }
}

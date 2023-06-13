import 'package:get_it/get_it.dart';
import 'package:haz_tu_huerto_mobile_2/config/locator.dart';
import 'package:haz_tu_huerto_mobile_2/models/garden/garden_details_dto.dart';
import 'package:haz_tu_huerto_mobile_2/models/garden/garden_response_dto.dart';
import 'package:haz_tu_huerto_mobile_2/models/garden/new_garden_dto.dart';
import 'package:haz_tu_huerto_mobile_2/models/question/new_question_dto.dart';
import 'package:haz_tu_huerto_mobile_2/repositories/garden_repository.dart';
import 'package:haz_tu_huerto_mobile_2/services/services.dart';
import 'package:image_picker/image_picker.dart';
import 'package:injectable/injectable.dart';



@Order(2)
@singleton
class GardenService {
  late GardenRepository _gardenRepository;
  late LocalStorageService _localStorageService;

  GardenService() {
    _gardenRepository = getIt<GardenRepository>();
    GetIt.I
        .getAsync<LocalStorageService>()
        .then((value) => _localStorageService = value);
  }

  Future<dynamic> findAll(int index) async {
    String? token = await _localStorageService.getFromDisk("user_token");

    if (token != null) {
      GardenResponseDto response = await _gardenRepository.findAll(index, token);
      return response;
    }
    throw Exception("There is an error in the service.");
  }

  
  Future<dynamic> findById(String id) async {
    String? token = await _localStorageService.getFromDisk("user_token");

    if (token != null) {
      GardenDetailsDto response = await _gardenRepository.findById(id, token);
      return response;
    }
    throw Exception("There is an error in the service.");
  }

   Future<GardenDetailsDto> create(
      List<XFile> file, NewGardenDto quest) async {
    String? token = await _localStorageService.getFromDisk("user_token");

    if (token != null) {
      GardenDetailsDto response = await _gardenRepository.create(file, quest);
      return response;
    }
    throw new Exception("Ha ocurrido un error en el servicio");
  }


  Future<GardenDetailsDto> update(
      List<XFile> file, NewGardenDto quest, String id) async {
    String? token = await _localStorageService.getFromDisk("user_token");

    if (token != null) {
      GardenDetailsDto response = await _gardenRepository.udpate(file, quest, id);
      return response;
    }
    throw new Exception("Ha ocurrido un error en el servicio");
  }

 
}
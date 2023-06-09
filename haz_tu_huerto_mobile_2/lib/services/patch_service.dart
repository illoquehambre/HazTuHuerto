import 'package:get_it/get_it.dart';
import 'package:haz_tu_huerto_mobile_2/config/locator.dart';
import 'package:haz_tu_huerto_mobile_2/models/garden/garden_details_dto.dart';
import 'package:haz_tu_huerto_mobile_2/models/garden/garden_response_dto.dart';
import 'package:haz_tu_huerto_mobile_2/models/garden/new_garden_dto.dart';
import 'package:haz_tu_huerto_mobile_2/models/patch/patch_details_dto.dart';
import 'package:haz_tu_huerto_mobile_2/models/question/new_question_dto.dart';
import 'package:haz_tu_huerto_mobile_2/repositories/garden_repository.dart';
import 'package:haz_tu_huerto_mobile_2/repositories/patch_repository.dart';
import 'package:haz_tu_huerto_mobile_2/services/services.dart';
import 'package:image_picker/image_picker.dart';
import 'package:injectable/injectable.dart';



@Order(2)
@singleton
class PatchService {
  late PatchRepository _patchRepository;
  late LocalStorageService _localStorageService;

  PatchService() {
    _patchRepository = getIt<PatchRepository>();
    GetIt.I
        .getAsync<LocalStorageService>()
        .then((value) => _localStorageService = value);
  }



  
  Future<dynamic> findById(String id) async {
    String? token = await _localStorageService.getFromDisk("user_token");

    if (token != null) {
      PatchDetailsDto response = await _patchRepository.findById(id, token);
      return response;
    }
    throw Exception("There is an error in the service.");
  }


/*
  Future<PatchDetailsDto> update(
      List<XFile> file, NewGardenDto quest, String id) async {
    String? token = await _localStorageService.getFromDisk("user_token");

    if (token != null) {
      GardenDetailsDto response = await _gardenRepository.udpate(file, quest, id);
      return response;
    }
    throw new Exception("Ha ocurrido un error en el servicio");
  }*/
}
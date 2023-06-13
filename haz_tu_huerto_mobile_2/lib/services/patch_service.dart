import 'package:get_it/get_it.dart';
import 'package:haz_tu_huerto_mobile_2/blocs/patch_history/patch_history_bloc.dart';
import 'package:haz_tu_huerto_mobile_2/config/locator.dart';
import 'package:haz_tu_huerto_mobile_2/models/patch/new_patch_dto.dart';
import 'package:haz_tu_huerto_mobile_2/models/patch/patch_details_dto.dart';
import 'package:haz_tu_huerto_mobile_2/models/patch/patch_history.dart';
import 'package:haz_tu_huerto_mobile_2/models/question/new_question_dto.dart';
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



  
  Future<dynamic> findById(int id) async {
    String? token = await _localStorageService.getFromDisk("user_token");

    if (token != null) {
      PatchDetailsDto response = await _patchRepository.findById(id, token);
      return response;
    }
    throw Exception("There is an error in the service.");
  }

  Future<dynamic> getHistory(int id) async {
    String? token = await _localStorageService.getFromDisk("user_token");

    if (token != null) {
      PatchHistoryDto response = await _patchRepository.getHistory(id, token);
      return response;
    }
    throw Exception("There is an error in the service.");
  }

   Future<PatchDetailsDto> create(
      List<XFile> file, NewPatchDto patch, int id) async {
    String? token = await _localStorageService.getFromDisk("user_token");

    if (token != null) {
      PatchDetailsDto response = await _patchRepository.create(file, patch, id);
      return response;
    }
    throw new Exception("Ha ocurrido un error en el servicio");
  }

    Future<PatchDetailsDto> divide( int id) async {
    String? token = await _localStorageService.getFromDisk("user_token");

    if (token != null) {
      PatchDetailsDto response = await _patchRepository.divide(id);
      return response;
    }
    throw new Exception("Ha ocurrido un error en el servicio");
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
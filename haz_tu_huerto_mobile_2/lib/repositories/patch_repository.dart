import 'dart:convert';
import 'package:get_it/get_it.dart';
import 'package:haz_tu_huerto_mobile_2/models/patch/new_patch_dto.dart';
import 'package:haz_tu_huerto_mobile_2/models/patch/patch_details_dto.dart';
import 'package:http/http.dart';
import 'package:image_picker/image_picker.dart';
import 'package:injectable/injectable.dart';

import '../rest/rest_client.dart';

@Order(-1)
@singleton
class PatchRepository {
  late RestAuthenticatedClient _client;

  PatchRepository() {
    _client = GetIt.I.get<RestAuthenticatedClient>();
    //_client = RestClient();
  }


  Future<dynamic> findById(int id, String token) async {
    String url = "/patch/$id";

    var response = await _client.get(url, token);

    if (response is NotFoundException) {
      String aux = "No se ha encontrado ningún post en esta página de...";
      return aux;
    }

    return PatchDetailsDto.fromJson(jsonDecode(response));
  }


  Future<dynamic> getHistory(int id, String token) async {
    String url = "/patch/history/$id";

    var response = await _client.get(url, token);

    if (response is NotFoundException) {
      String aux = "No se ha encontrado ningún post en esta página de...";
      return aux;
    }

    return PatchDetailsDto.fromJson(jsonDecode(response));
  }

  Future<dynamic> create(List<XFile> files, NewPatchDto patch, int id) async {
    String url = "/patch/garden/$id";

    StreamedResponse response =
        await _client.postMultipartChetada(url, files, patch, 'newPatch');

    var stringResponse = await response.stream.bytesToString();

    return PatchDetailsDto.fromJson(jsonDecode(stringResponse));
  }

 Future<dynamic> divide(int id) async {
    String url = "/patch/divide/$id";

    StreamedResponse response =
        await _client.post(url, null);

    var stringResponse = await response.stream.bytesToString();

    return PatchDetailsDto.fromJson(jsonDecode(stringResponse));
  }
/*
  Future<dynamic> udpate(List<XFile> files, NewGardenDto garden, String id) async {
    String url = "/patch/$id";

    StreamedResponse response =
        await _client.putMultipartChetada(url, files, garden, 'editPatch');

    var stringResponse = await response.stream.bytesToString();

    return GardenDetailsDto.fromJson(jsonDecode(stringResponse));
  }
*/
  
}

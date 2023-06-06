import 'dart:convert';
import 'package:get_it/get_it.dart';
import 'package:haz_tu_huerto_mobile_2/models/garden/garden_details_dto.dart';
import 'package:haz_tu_huerto_mobile_2/models/garden/garden_response_dto.dart';
import 'package:haz_tu_huerto_mobile_2/models/garden/new_garden_dto.dart';
import 'package:http/http.dart';
import 'package:image_picker/image_picker.dart';
import 'package:injectable/injectable.dart';

import '../rest/rest_client.dart';

@Order(-1)
@singleton
class GardenRepository {
  late RestAuthenticatedClient _client;

  GardenRepository() {
    _client = GetIt.I.get<RestAuthenticatedClient>();
    //_client = RestClient();
  }

  Future<dynamic> findAll(int index, String token) async {
    String url = "/vegetableGarden?page=$index";

    var response = await _client.get(url, token);

    if (response is NotFoundException) {
      String aux = "No se ha encontrado ningún post en esta página de...";
      return aux;
    }

    return GardenResponseDto.fromJson(jsonDecode(response));
  }

  Future<dynamic> findById(String id, String token) async {
    String url = "/vegetableGarden/$id";

    var response = await _client.get(url, token);

    if (response is NotFoundException) {
      String aux = "No se ha encontrado ningún post en esta página de...";
      return aux;
    }

    return GardenDetailsDto.fromJson(jsonDecode(response));
  }

  Future<dynamic> create(List<XFile> files, NewGardenDto garden) async {
    String url = "/vegetableGarden";

    StreamedResponse response =
        await _client.postMultipartChetada(url, files, garden, 'newGarden');

    var stringResponse = await response.stream.bytesToString();

    return GardenDetailsDto.fromJson(jsonDecode(stringResponse));
  }

  Future<dynamic> udpate(List<XFile> files, NewGardenDto garden, String id) async {
    String url = "/vegetableGarden/$id";

    StreamedResponse response =
        await _client.putMultipartChetada(url, files, garden, 'editGarden');

    var stringResponse = await response.stream.bytesToString();

    return GardenDetailsDto.fromJson(jsonDecode(stringResponse));
  }

  
}

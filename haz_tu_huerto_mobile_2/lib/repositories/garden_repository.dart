import 'dart:convert';
import 'package:get_it/get_it.dart';
import 'package:haz_tu_huerto_mobile_2/models/garden/garden_response_dto.dart';
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

}

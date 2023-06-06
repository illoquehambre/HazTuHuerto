import 'dart:convert';
import 'package:get_it/get_it.dart';
import 'package:haz_tu_huerto_mobile_2/models/garden/garden_details_dto.dart';
import 'package:injectable/injectable.dart';

import '../rest/rest_client.dart';

@Order(-1)
@singleton
class ImageRepository {
  late RestAuthenticatedClient _client;

  ImageRepository() {
    _client = GetIt.I.get<RestAuthenticatedClient>();
    //_client = RestClient();
  }



  Future<dynamic> download(String id, String token) async {
    String url = "/download/$id";

    var response = await _client.get(url, token);

    if (response is NotFoundException) {
      String aux = "No se ha encontrado ningún archivo...";
      return aux;
    }

    return GardenDetailsDto.fromJson(jsonDecode(response));
  }





  
}

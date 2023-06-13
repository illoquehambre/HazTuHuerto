import 'dart:convert';
import 'package:get_it/get_it.dart';
import 'package:haz_tu_huerto_mobile_2/models/note/new_note_dto.dart';
import 'package:haz_tu_huerto_mobile_2/models/note/note_response_dto.dart';
import 'package:http/http.dart';
import 'package:image_picker/image_picker.dart';
import 'package:injectable/injectable.dart';

import '../rest/rest_client.dart';

@Order(-1)
@singleton
class NoteRepository {
  late RestAuthenticatedClient _client;

  NoteRepository() {
    _client = GetIt.I.get<RestAuthenticatedClient>();
    //_client = RestClient();
  }

  

Future<dynamic> findById(String id, String token) async {
    String url = "/note/$id";

    var response = await _client.get(url, token);

    if (response is NotFoundException) {
      String aux = "No se ha encontrado ningún post en esta página de...";
      return aux;
    }

    return NoteResponseDto.fromJson(jsonDecode(response));
  }


  Future<dynamic> create( NewNoteDto note, int id) async {
    String url = "/note/$id";

    String response =
        await _client.post(url, note);

   // var stringResponse = await response.stream.bytesToString();

    return NoteResponseDto.fromJson(jsonDecode(response));
  }
}

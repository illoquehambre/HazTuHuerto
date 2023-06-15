import 'package:get_it/get_it.dart';
import 'package:haz_tu_huerto_mobile_2/config/locator.dart';
import 'package:haz_tu_huerto_mobile_2/models/note/new_note_dto.dart';
import 'package:haz_tu_huerto_mobile_2/models/note/note_response_dto.dart';
import 'package:haz_tu_huerto_mobile_2/repositories/note_repository.dart';
import 'package:haz_tu_huerto_mobile_2/services/services.dart';
import 'package:injectable/injectable.dart';

@Order(2)
@singleton
class NoteService {
  late NoteRepository _noteRepository;
  late LocalStorageService _localStorageService;

  NoteService() {
    _noteRepository = getIt<NoteRepository>();
    GetIt.I
        .getAsync<LocalStorageService>()
        .then((value) => _localStorageService = value);
  }

  Future<dynamic> findById(int id) async {
    String? token = await _localStorageService.getFromDisk("user_token");

    if (token != null) {
      NoteResponseDto response = await _noteRepository.findById(id, token);
      return response;
    }
    throw Exception("There is an error in the service.");
  }

  Future<NoteResponseDto> create(int id, NewNoteDto note) async {
    String? token = await _localStorageService.getFromDisk("user_token");

    if (token != null) {
      NoteResponseDto response = await _noteRepository.create(note, id);
      return response;
    }
    throw new Exception("Ha ocurrido un error en el servicio");
  }

  void delete(
    int id,
  ) async {
    _noteRepository.delete(id);
  }
}

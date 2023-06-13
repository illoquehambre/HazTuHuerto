import 'dart:async';
import 'package:flutter_form_bloc/flutter_form_bloc.dart';
import 'package:haz_tu_huerto_mobile_2/models/note/new_note_dto.dart';
import 'package:haz_tu_huerto_mobile_2/services/note_service.dart';

class NewNoteFormBloc extends FormBloc<String, String> {
  final title = TextFieldBloc();
  final content = TextFieldBloc(validators: [
    FieldBlocValidators.required,
  ]);
  final int id;
  final NoteService _noteService;

  NewNoteFormBloc(NoteService noteService, this.id)
      // ignore: unnecessary_null_comparison
      : assert(noteService != null),
        _noteService = noteService {
    addFieldBlocs(fieldBlocs: [title, content]);
  }

  @override
  FutureOr<void> onSubmitting() {
    emitLoading();
    try {
      _noteService
          .create( id, NewNoteDto(title: title.value, content: content.value))
          .then((value) {
        
      });
    } catch (e) {
      throw new Exception("Ha ocurrido un error en el bloc");
    }
  }
}

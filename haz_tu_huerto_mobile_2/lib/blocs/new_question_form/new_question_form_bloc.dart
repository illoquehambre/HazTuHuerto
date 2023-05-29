import 'dart:async';
import 'package:flutter_form_bloc/flutter_form_bloc.dart';
import 'package:image_picker/image_picker.dart';

import 'package:haz_tu_huerto_mobile_2/models/question/new_question_dto.dart';
import 'package:haz_tu_huerto_mobile_2/services/question_service.dart';

class NewQuestFormBloc extends FormBloc<String, String> {
  final title = TextFieldBloc();
  final content = TextFieldBloc(validators: [
    FieldBlocValidators.required,
  ]);
  late List<XFile> files = [];
  final QuestionService _questService;

  NewQuestFormBloc(QuestionService questService)
      // ignore: unnecessary_null_comparison
      : assert(questService != null),
        _questService = questService {
    addFieldBlocs(fieldBlocs: [title, content]);
  }

  @override
  FutureOr<void> onSubmitting() {
    emitLoading();
    try {
      _questService
          .create(files, NewQuestionDto(title: title.value, content: content.value))
          .then((value) {
        
      });
    } catch (e) {
      throw new Exception("Ha ocurrido un error en el bloc");
    }
  }
}

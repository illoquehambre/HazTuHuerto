import 'dart:async';
import 'package:flutter_form_bloc/flutter_form_bloc.dart';
import 'package:haz_tu_huerto_mobile_2/services/patch_service.dart';

class DividePatchFormBloc extends FormBloc<String, String> {
  
  final int id;
  final PatchService _patchService;

  DividePatchFormBloc(PatchService patchService, this.id)
      // ignore: unnecessary_null_comparison
      : assert(patchService != null),
        _patchService = patchService {
  }

  @override
  FutureOr<void> onSubmitting() {
    emitLoading();
    try {
      _patchService
          .divide(id)
          .then((value) {
        
      });
    } catch (e) {
      throw new Exception("Ha ocurrido un error en el bloc");
    }
  }
}

import 'package:bloc/bloc.dart';
import 'package:equatable/equatable.dart';
import 'package:haz_tu_huerto_mobile_2/models/patch/patch_history.dart';
import 'package:haz_tu_huerto_mobile_2/services/patch_service.dart';

part 'patch_history_event.dart';
part 'patch_history_state.dart';

class PatchHistoryBloc extends Bloc<PatchHistoryEvent, PatchHistoryState> {
  dynamic fetchedPatch;
  final PatchService _PatchService;
  
    Future<dynamic> _fetchPatchHistory(int id) async {
    final response = await _PatchService.getHistory(id);
    return response;
  }
  Future<void> _onPatchHistoryFetched(
    PatchHistoryInitialEvent event,
    Emitter<PatchHistoryState> emit,
  ) async {
    try {
      if (state is PatchHistoryInitial) {
        final response = await _fetchPatchHistory(event.id);

        if (response is PatchHistoryDto) {
          fetchedPatch = response;
         
        } else if (response is String) {
          fetchedPatch = response;
        }

        emit(
          PatchHistorySucces(
            patch: fetchedPatch,
          ),
        );
      }
    } catch (_) {
      print(_);
      emit(PatchHistoryFailure(error: "$_"));
    }
  }

    PatchHistoryBloc(PatchService patchService)
      // ignore: unnecessary_null_comparison
      : assert(PatchService != null),
        _PatchService = patchService,
        super(PatchHistoryInitial()) {
    on<PatchHistoryInitialEvent>((event, emit ) async {
       await _onPatchHistoryFetched(event, emit);
    });
  }
 
}

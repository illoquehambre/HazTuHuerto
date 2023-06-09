import 'package:bloc/bloc.dart';
import 'package:equatable/equatable.dart';
import 'package:haz_tu_huerto_mobile_2/models/patch/patch_details_dto.dart';
import 'package:haz_tu_huerto_mobile_2/services/patch_service.dart';

part 'patch_details_event.dart';
part 'patch_details_state.dart';

class PatchDetailsBloc extends Bloc<PatchDetailsEvent, PatchDetailsState> {
  dynamic fetchedPatch;
  final PatchService _PatchService;
  
    Future<dynamic> _fetchPatchDetails(String id) async {
    final response = await _PatchService.findById(id);
    return response;
  }
  Future<void> _onPatchDetailsFetched(
    PatchDetailsInitialEvent event,
    Emitter<PatchDetailsState> emit,
  ) async {
    try {
      if (state is PatchDetailsInitial) {
        final response = await _fetchPatchDetails(event.id);

        if (response is PatchDetailsDto) {
          fetchedPatch = response;
         
        } else if (response is String) {
          fetchedPatch = response;
        }

        emit(
          PatchDetailsSucces(
            patch: fetchedPatch,
          ),
        );
      }
    } catch (_) {
      print(_);
      emit(PatchDetailsFailure(error: "$_"));
    }
  }

    PatchDetailsBloc(PatchService patchService)
      // ignore: unnecessary_null_comparison
      : assert(PatchService != null),
        _PatchService = patchService,
        super(PatchDetailsInitial()) {
    on<PatchDetailsInitialEvent>((event, emit ) async {
       await _onPatchDetailsFetched(event, emit);
    });
  }
 
}

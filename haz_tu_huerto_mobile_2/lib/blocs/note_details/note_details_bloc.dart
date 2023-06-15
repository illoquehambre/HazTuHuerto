import 'package:bloc/bloc.dart';
import 'package:equatable/equatable.dart';
import 'package:haz_tu_huerto_mobile_2/models/note/note_response_dto.dart';
import 'package:haz_tu_huerto_mobile_2/services/note_service.dart';

part 'note_details_event.dart';
part 'note_details_state.dart';

class NoteDetailsBloc extends Bloc<NoteDetailsEvent, NoteDetailsState> {
  dynamic fetchedNote;
  final NoteService _NoteService;
  
    Future<dynamic> _fetchNoteDetails(int id) async {
    final response = await _NoteService.findById(id);
    return response;
  }
  Future<void> _onNoteDetailsFetched(
    NoteDetailsInitialEvent event,
    Emitter<NoteDetailsState> emit,
  ) async {
    try {
      if (state is NoteDetailsInitial) {
        final response = await _fetchNoteDetails(event.id);

        if (response is NoteResponseDto) {
          fetchedNote = response;
         
        } else if (response is String) {
          fetchedNote = response;
        }

        emit(
          NoteDetailsSucces(
            note: fetchedNote,
          ),
        );
      }
    } catch (_) {
      print(_);
      emit(NoteDetailsFailure(error: "$_"));
    }
  }

    NoteDetailsBloc(NoteService noteService)
      // ignore: unnecessary_null_comparison
      : assert(NoteService != null),
        _NoteService = noteService,
        super(NoteDetailsInitial()) {
    on<NoteDetailsInitialEvent>((event, emit ) async {
       await _onNoteDetailsFetched(event, emit);
    });
  }
 
}

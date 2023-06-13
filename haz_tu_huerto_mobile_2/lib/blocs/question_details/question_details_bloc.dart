import 'package:bloc/bloc.dart';
import 'package:equatable/equatable.dart';
import 'package:haz_tu_huerto_mobile_2/models/question/question_detail_dto.dart';
import 'package:haz_tu_huerto_mobile_2/services/question_service.dart';

part 'question_details_event.dart';
part 'question_details_state.dart';

class QuestionDetailsBloc extends Bloc<QuestionDetailsEvent, QuestionDetailsState> {
  dynamic fetchedQuestion;
  final QuestionService _QuestionService;
  
    Future<dynamic> _fetchQuestionDetails(String id) async {
    final response = await _QuestionService.findById(id);
    return response;
  }
  Future<void> _onQuestionDetailsFetched(
    QuestionDetailsInitialEvent event,
    Emitter<QuestionDetailsState> emit,
  ) async {
    try {
      if (state is QuestionDetailsInitial) {
        final response = await _fetchQuestionDetails(event.id);

        if (response is QuestionDetailsDto) {
          fetchedQuestion = response;
         
        } else if (response is String) {
          fetchedQuestion = response;
        }

        emit(
          QuestionDetailsSucces(
            question: fetchedQuestion,
          ),
        );
      }
    } catch (_) {
      print(_);
      emit(QuestionDetailsFailure(error: "$_"));
    }
  }

    QuestionDetailsBloc(QuestionService questionService)
      // ignore: unnecessary_null_comparison
      : assert(QuestionService != null),
        _QuestionService = questionService,
        super(QuestionDetailsInitial()) {
    on<QuestionDetailsInitialEvent>((event, emit ) async {
       await _onQuestionDetailsFetched(event, emit);
    });
  }
 
}

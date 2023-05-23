import 'package:bloc/bloc.dart';
import 'package:bloc_concurrency/bloc_concurrency.dart';
import 'package:equatable/equatable.dart';
import 'package:haz_tu_huerto_mobile_2/models/question_response_dto.dart';
import 'package:stream_transform/stream_transform.dart';

import '../../services/question_service.dart';

part 'question_event.dart';
part 'question_state.dart';


const throttleDuration = Duration(milliseconds: 100);

EventTransformer<E> throttleDroppable<E>(Duration duration) {
  return (events, mapper) {
    return droppable<E>().call(events.throttle(duration), mapper);
  };
}

class QuestionBloc extends Bloc<QuestionEvent, QuestionState> {
  int it = 0;
  int itF = 0;
  var hasReachedMax = false;
  var hasReachedMaxF = false;
  dynamic fetchedQuestion;
  dynamic fetchedFollowedQuestion;
  final QuestionService _QuestionService;

  Future<dynamic> _fetchQuestion(int startIndex) async {
    final response = await _QuestionService.findAll(startIndex);
    return response;
  }
  
/*
    on<LikeAQuestion>(
      (event, emit) async {
        await _likePost(event.questionId);
      },
    );
  }
*/

  Future<void> _onQuestionFetched(
    QuestionEvent event,
    Emitter<QuestionState> emit,
  ) async {
    try {
      if (state is QuestionInitial) {
        final response = await _fetchQuestion(it);

        if (response is QuestionResponseDto) {
          hasReachedMax = response.last;
          fetchedQuestion = response.content;
          if (response.last != true) it += 1;
        } else if (response is String) {
          fetchedQuestion = response;
        }

        emit(
          QuestionSucces(
            questions: fetchedQuestion,
          ),
        );
      }
    } catch (_) {
      print(_);
      emit(QuestionFailure(error: "${_}"));
    }
  }

  Future<void> _onScrollQuestion(
    QuestionEvent event,
    Emitter<QuestionState> emit,
  ) async {
    if (hasReachedMax == false) {
      final response = await _fetchQuestion(it);
      response.content.isEmpty
          ? hasReachedMax = true
          : {
              emit(QuestionSucces(
                  questions: List.of(fetchedQuestion)..addAll(response.content))),
              hasReachedMax = response.last,
              if (response.last != true) it += 1
            };
    }
  }



  Future<void> _onQuestionRefresh(
    QuestionEvent event,
    Emitter<QuestionState> emit,
  ) async {
    final response = await _fetchQuestion(0);

    if (response is QuestionResponseDto) {
      response.content.isEmpty
          ? hasReachedMaxF = true
          : {
              emit(
                QuestionSucces(
                  questions: response.content,
                  
                ),
              ),
              it = 1,
              hasReachedMax = response.last,
              fetchedQuestion = response.content,
            };
    } else {
      emit(
        QuestionSucces(
          questions: fetchedQuestion,
        ),
      );
    }
  }

  QuestionBloc(QuestionService questionService)
      // ignore: unnecessary_null_comparison
      : assert(questionService != null),
        _QuestionService = questionService,
        super(QuestionInitial()) {
    on<QuestionInitialEvent>((event, emit) async {
      await _onQuestionFetched(event, emit);
    });

    on<QuestionScrollEvent>(
      (event, emit) async {
        await _onScrollQuestion(event, emit);
      },
      transformer: throttleDroppable(throttleDuration),
    );

    on<QuestionRefreshEvent>(
      (event, emit) async {
        await _onQuestionRefresh(event, emit);
      },
    );

  
/*
  Future<void> _likePost(int postId) async {
    final aux = await _QuestionService.likePost(postId);

    for (var element in fetchedQuestion) {
      if (element.id == aux.id) {
        element.likedByUser = aux.likedByUser;
        element.usersWhoLiked = aux.usersWhoLiked;
      }
    }

    if (fetchedFollowedQuestion is List<GetPostDto>) {
      for (var element in fetchedFollowedQuestion) {
        if (element.id == aux.id) {
          element.likedByUser = aux.likedByUser;
          element.usersWhoLiked = aux.usersWhoLiked;
        }
      }
    }
  }
  */
}
}

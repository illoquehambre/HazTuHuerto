part of 'question_bloc.dart';

abstract class QuestionEvent extends Equatable {
  const QuestionEvent();

  @override
  List<Object> get props => [];
}
class QuestionInitialEvent extends QuestionEvent {}

class QuestionScrollEvent extends QuestionEvent {}

class QuestionRefreshEvent extends QuestionEvent {}


class LikeAQuestion extends QuestionEvent {
  final int questionId;

  const LikeAQuestion(this.questionId);
}

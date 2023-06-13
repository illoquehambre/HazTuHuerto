part of 'question_details_bloc.dart';

abstract class QuestionDetailsEvent extends Equatable {
  const QuestionDetailsEvent();

  @override
  List<Object> get props => [];
}
class QuestionDetailsInitialEvent extends QuestionDetailsEvent {
   final String id;

  const QuestionDetailsInitialEvent({required this.id});

}





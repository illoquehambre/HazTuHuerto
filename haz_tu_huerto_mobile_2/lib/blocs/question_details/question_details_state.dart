part of 'question_details_bloc.dart';

abstract class QuestionDetailsState extends Equatable {
  const QuestionDetailsState();

  @override
  List<Object> get props => [];
}

class QuestionDetailsInitial extends QuestionDetailsState {}

class QuestionDetailsSucces extends QuestionDetailsState {
  final dynamic question;

  const QuestionDetailsSucces({required this.question});

  @override
  List<Object> get props => [question];
}

class QuestionDetailsLoading extends QuestionDetailsState {}

class QuestionDetailsFailure extends QuestionDetailsState {
  final String error;

  const QuestionDetailsFailure({required this.error});

  @override
  List<Object> get props => [error];
}

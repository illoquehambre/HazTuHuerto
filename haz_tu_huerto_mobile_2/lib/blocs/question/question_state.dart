part of 'question_bloc.dart';

abstract class QuestionState extends Equatable {
  const QuestionState();
  
  @override
  List<Object> get props => [];
}

class QuestionInitial extends QuestionState {}

class QuestionSucces extends QuestionState {
  final dynamic questions;

  QuestionSucces({required this.questions});

  @override
  List<Object> get props => [questions];
}

class QuestionLoading extends QuestionState {}

class QuestionFailure extends QuestionState {
  final String error;

  QuestionFailure({required this.error});

  @override
  List<Object> get props => [error];
}

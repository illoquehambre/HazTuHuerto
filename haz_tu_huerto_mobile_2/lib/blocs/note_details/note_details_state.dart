part of 'note_details_bloc.dart';

abstract class NoteDetailsState extends Equatable {
  const NoteDetailsState();

  @override
  List<Object> get props => [];
}

class NoteDetailsInitial extends NoteDetailsState {}

class NoteDetailsSucces extends NoteDetailsState {
  final dynamic note;

  const NoteDetailsSucces({required this.note});

  @override
  List<Object> get props => [note];
}

class NoteDetailsLoading extends NoteDetailsState {}

class NoteDetailsFailure extends NoteDetailsState {
  final String error;

  const NoteDetailsFailure({required this.error});

  @override
  List<Object> get props => [error];
}

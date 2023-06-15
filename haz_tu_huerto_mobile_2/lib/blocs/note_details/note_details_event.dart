part of 'note_details_bloc.dart';

abstract class NoteDetailsEvent extends Equatable {
  const NoteDetailsEvent();

  @override
  List<Object> get props => [];
}
class NoteDetailsInitialEvent extends NoteDetailsEvent {
   final int id;

  const NoteDetailsInitialEvent({required this.id});

}





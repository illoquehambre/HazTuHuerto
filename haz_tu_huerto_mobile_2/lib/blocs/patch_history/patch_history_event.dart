part of 'patch_history_bloc.dart';

abstract class PatchHistoryEvent extends Equatable {
  const PatchHistoryEvent();

  @override
  List<Object> get props => [];
}
class PatchHistoryInitialEvent extends PatchHistoryEvent {
   final int id;

  const PatchHistoryInitialEvent({required this.id});

}





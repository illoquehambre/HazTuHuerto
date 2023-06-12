part of 'patch_details_bloc.dart';

abstract class PatchDetailsEvent extends Equatable {
  const PatchDetailsEvent();

  @override
  List<Object> get props => [];
}
class PatchDetailsInitialEvent extends PatchDetailsEvent {
   final int id;

  const PatchDetailsInitialEvent({required this.id});

}





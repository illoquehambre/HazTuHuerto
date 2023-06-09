part of 'patch_details_bloc.dart';

abstract class PatchDetailsEvent extends Equatable {
  const PatchDetailsEvent();

  @override
  List<Object> get props => [];
}
class PatchDetailsInitialEvent extends PatchDetailsEvent {
   final String id;

  const PatchDetailsInitialEvent({required this.id});

}






import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:haz_tu_huerto_mobile_2/blocs/patch_history/patch_history_bloc.dart';
import 'package:haz_tu_huerto_mobile_2/config/locator.dart';
import 'package:haz_tu_huerto_mobile_2/services/patch_service.dart';
import 'package:haz_tu_huerto_mobile_2/widget/patch_history.dart';
import 'package:loading_animation_widget/loading_animation_widget.dart';

class PatchHistoryPage extends StatelessWidget {
  final int id;
  const PatchHistoryPage({super.key,required this.id});

  @override
  Widget build(BuildContext context) {
    return BlocProvider(
      create: (context) {
        final patchService = getIt<PatchService>();
        return PatchHistoryBloc(patchService)..add(PatchHistoryInitialEvent(id: id));
      },
      child: const EventsPageSFG(),
    );
  }
}

class EventsPageSFG extends StatefulWidget {
  const EventsPageSFG({super.key});

  @override
  State<EventsPageSFG> createState() => _EventsPageSFGState();
}

class _EventsPageSFGState extends State<EventsPageSFG> {
  @override
  Widget build(BuildContext context) {
    return BlocBuilder<PatchHistoryBloc, PatchHistoryState>(
      builder: (context,  state) {
        if (state is PatchHistorySucces) {
          return PatchHistory(
            context: context,
            patch: state.patch,
          );
        } else if (state is PatchHistoryFailure) {
          return const Center(
            child: Text("Ha ocurrido un error a la hora de cargar los posts"),
          );
        } else {
          return Center(
            child: LoadingAnimationWidget.staggeredDotsWave(
                color: Colors.white, size: 40),
          );
        }
      },
    );
  }
}
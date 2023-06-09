
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:haz_tu_huerto_mobile_2/blocs/garden_details/garden_details_bloc.dart';
import 'package:haz_tu_huerto_mobile_2/blocs/patch_details/patch_details_bloc.dart';
import 'package:haz_tu_huerto_mobile_2/config/locator.dart';
import 'package:haz_tu_huerto_mobile_2/services/garden_service.dart';
import 'package:haz_tu_huerto_mobile_2/services/patch_service.dart';
import 'package:haz_tu_huerto_mobile_2/widget/garden_details.dart';
import 'package:haz_tu_huerto_mobile_2/widget/patch_details.dart';
import 'package:loading_animation_widget/loading_animation_widget.dart';

class PatchDetailsPage extends StatelessWidget {
  final String id;
  const PatchDetailsPage({super.key,required this.id});

  @override
  Widget build(BuildContext context) {
    return BlocProvider(
      create: (context) {
        final patchService = getIt<PatchService>();
        return PatchDetailsBloc(patchService)..add(PatchDetailsInitialEvent(id: id));
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
    return BlocBuilder<PatchDetailsBloc, PatchDetailsState>(
      builder: (context,  state) {
        if (state is PatchDetailsSucces) {
          return PatchDetails(
            context: context,
            patch: state.patch,
          );
        } else if (state is GardenDetailsFailure) {
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
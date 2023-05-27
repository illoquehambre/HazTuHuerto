
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:haz_tu_huerto_mobile_2/blocs/garden_details/garden_details_bloc.dart';
import 'package:haz_tu_huerto_mobile_2/config/locator.dart';
import 'package:haz_tu_huerto_mobile_2/services/garden_service.dart';
import 'package:haz_tu_huerto_mobile_2/widget/garden_details.dart';
import 'package:loading_animation_widget/loading_animation_widget.dart';

class GardenDetailsPage extends StatelessWidget {
  final String id;
  const GardenDetailsPage({super.key,required this.id});

  @override
  Widget build(BuildContext context) {
    return BlocProvider(
      create: (context) {
        final gardenService = getIt<GardenService>();
        return GardenDetailsBloc(gardenService)..add(GardenDetailsInitialEvent(id: id));
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
    return BlocBuilder<GardenDetailsBloc, GardenDetailsState>(
      builder: (context,  state) {
        if (state is GardenDetailsSucces) {
          return GardenDetails(
            context: context,
            garden: state.garden,
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
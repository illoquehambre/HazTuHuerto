import 'package:flutter/material.dart';
import 'package:haz_tu_huerto_mobile_2/blocs/garden/garden_bloc.dart';
import 'package:haz_tu_huerto_mobile_2/models/garden/garden_details_dto.dart';
import 'package:haz_tu_huerto_mobile_2/pages/update_garden_page.dart';
import 'package:haz_tu_huerto_mobile_2/widget/bottom_loader.dart';
import 'package:haz_tu_huerto_mobile_2/widget/patch.dart';

class GardenDetails extends StatefulWidget {
  final GardenDetailsDto garden;
  final BuildContext context;

  const GardenDetails({super.key, required this.garden, required this.context});

  @override
  State<GardenDetails> createState() => _GardenDetailsState();
}

class _GardenDetailsState extends State<GardenDetails> {
  final _scrollController = ScrollController();

  @override
  void initState() {
    // TODO: implement initState
    super.initState();
    _scrollController.addListener(_onScroll);
  }

  @override
  Widget build(BuildContext context) {
    final textTheme = Theme.of(context).textTheme;
    return Material(
      child: Center(
        child: SingleChildScrollView(
          child: Column(
            children: <Widget>[
              Text(
                widget.garden.id,
                style: textTheme.bodySmall,
              ),
              Text(widget.garden.name, style: textTheme.bodySmall),
        
              ListView.builder(
                itemBuilder: (BuildContext context, int index) {
                  return index >= widget.garden.patchList.length
                      ? const BottomLoader()
                      : Patch(num:1, patch: widget.garden.patchList[index],
                      context: context,);
                },
                itemCount: widget.garden.patchList.length),
              const SizedBox(
                height: 12,
              ),
              ElevatedButton(
                child: const Text('Update'),
                onPressed: () {
                  Navigator.push(
                    context,
                    PageRouteBuilder(
                      transitionDuration: const Duration(milliseconds: 500),
                      transitionsBuilder:
                          (context, animation, secondaryAnimation, child) {
                        return FadeTransition(
                          opacity: animation,
                          child: child,
                        );
                      },
                      pageBuilder: (context, animation, secondaryAnimation) {
                        return UpdateGardenPage(id: widget.garden.id);
                      },
                    ),
                  );
                },
              ),
              /*
              ElevatedButton(
                  onPressed: () async {
                    print("Check");
                    JwtAuthenticationService service =
                        getIt<JwtAuthenticationService>();
                    await service.getCurrentUser();
                  },
                  child: const Text('Check'))
                  */
            ],
          ),
        ),
      ),
    );
  }

  _onScroll() {
    setState(() {});
  }
}

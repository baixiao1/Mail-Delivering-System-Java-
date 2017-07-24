## How to run
- There is a runnable jar file in `jar` folder. Just double click it to run. Or use `java-jar KelburnPostalService.jar` in console to run it.

### Source code in `KPSSoftware` folder:

- `src` is where the source code files are.
> - `src/model` is for models
> - `src/view` is for views
> - `src/controller` is for controllers
> - `src/main` is for executable classes

- `resources` is where the resource files are.
> - `resources/css` is for the CSS file
> - `resources/fxml` is for JavaFX fxml files
> - `resources/img` is for images
> - `resources/xml` is for prepopulating data

- `test` is where the unit tests are.

- `lib` is where the external libraries are.

##### Notice:

- Several XML files will be generated in the execution path if any of the existing data is updated. This is because XML files in `resources` folder are readonly.
- The programme will try to read XML files in the execution path first. If none can be found, the XML files in `resources` will be read in.

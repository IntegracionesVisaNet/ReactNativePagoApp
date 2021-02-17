# ReactNativePagoApp
Proyecto de ejemplo de ReactNative con el SDK Nativo de Niubiz

## Información
Bridge desarrollado solo para Android

## Ejecutar el proyecto
```sh
git clone https://github.com/IntegracionesVisaNet/ReactNativePagoApp
cd ReactNativePagoApp
npm i
npx react-native run-android
```
## Pasos para integrarse
- Abrir el proyecto android de su aplicación de React Native. Se recomienda usar Android Studio.
- Crear un archivo de módulo nativo personalizado, lo nombraremos [NiubizModule.java](https://github.com/IntegracionesVisaNet/ReactNativePagoApp/blob/master/android/app/src/main/java/com/reactnativepagoapp/NiubizModule.java)
- Realizar la integración del SDK Nativo de Niubiz, puede encontrar los pasos a seguir en el siguiente [enlace](https://desarrolladores.niubiz.com.pe/pago-app/)
- Crear un archivo para registrar el módulo nativo personalizado, lo llamaremos [NiubizPackage.java](https://github.com/IntegracionesVisaNet/ReactNativePagoApp/blob/master/android/app/src/main/java/com/reactnativepagoapp/NiubizPackage.java)
- Registramos el package de Niubiz en el [MainApplication](https://github.com/IntegracionesVisaNet/ReactNativePagoApp/blob/master/android/app/src/main/java/com/reactnativepagoapp/MainApplication.java)
```sh
packages.add(new NiubizPackage());
```
### Invocar nuestro módulo nativo personalizado
- Creamos el módulo de Niubiz
```sh
import { NativeModules } from 'react-native';
module.exports = NativeModules.NiubizModule;
```
- Invocamos a nuestro método
```sh
NiubizModule.payWithNiubiz(result, amount, purchase).then(niubizResponse => {
  console.log('niubizResponse: ', niubizResponse);
  var jsonResponse = JSON.parse(niubizResponse);
  if (jsonResponse.dataMap != undefined) {
    console.log('Mensaje: ', jsonResponse.dataMap.ACTION_DESCRIPTION);
  } else {
    console.log('Mensaje: ', jsonResponse.data.ACTION_DESCRIPTION);
  }
})
```

## Más información
- https://reactnative.dev/docs/native-modules-android

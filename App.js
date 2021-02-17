/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow strict-local
 */

import React from 'react';
import {
  SafeAreaView,
  StyleSheet,
  ScrollView,
  View,
  Text,
  StatusBar,
  TextInput,
  Button,
  Alert
} from 'react-native';

import { Colors } from 'react-native/Libraries/NewAppScreen';

import NiubizModule from './NiubizModule';

const App: () => React$Node = () => {

  const [value, onChangeText] = React.useState('1');
  const Separator = () => (
    <View style={styles.separator} />
  );

  const showToast = async (amount) => {

    var purchase = "23498458";

    try {
      var requestOptions = {
        method: 'POST',
        headers: {
          'Content-Type': 'text/plain',
          'Authorization': 'Basic Z2lhbmNhZ2FsbGFyZG9AZ21haWwuY29tOkF2MyR0cnV6'
        },
        redirect: 'follow'
      };

      fetch("https://apitestenv.vnforapps.com/api.security/v1/security", requestOptions)
        .then(response => response.text())
        .then(result =>
          NiubizModule.payWithNiubiz(result, amount, purchase).then(niubizResponse => {
            console.log('niubizResponse: ', niubizResponse);
            var jsonResponse = JSON.parse(niubizResponse);
            if (jsonResponse.dataMap != undefined) {
              console.log('Mensaje: ', jsonResponse.dataMap.ACTION_DESCRIPTION);
            } else {
              console.log('Mensaje: ', jsonResponse.data.ACTION_DESCRIPTION);
            }
          })
        )
        .catch(error => console.log('error', error));
    } catch (error) {
      console.error(error);
    }
  }
  return (
    <>
      <StatusBar barStyle="dark-content" />
      <SafeAreaView>
        <ScrollView
          contentInsetAdjustmentBehavior="automatic"
          style={styles.scrollView}>
          {global.HermesInternal == null ? null : (
            <View style={styles.engine}>
              <Text style={styles.footer}>Engine: Hermes</Text>
            </View>
          )}
          <View style={styles.body}>
            <View style={styles.sectionContainer}>
              <Text style={styles.sectionTitle}>Pago App - React Native</Text>
              <Text style={styles.sectionDescription}>
                <Text style={styles.highlight}>Importe</Text>
              </Text>
              <TextInput keyboardType='numeric' style={{ height: 40, borderColor: 'gray', borderWidth: 1 }} value={value} onChangeText={text => onChangeText(text)} />
              <Separator />
              {/* <Button title="Pagar" onPress={() => Alert.alert('Simple Button pressed' + value)} /> */}
              <Button title="Pagar" onPress={() => showToast(value)} />
            </View>
          </View>
        </ScrollView>
      </SafeAreaView>
    </>
  );
};

const styles = StyleSheet.create({
  scrollView: {
    backgroundColor: Colors.lighter,
  },
  engine: {
    position: 'absolute',
    right: 0,
  },
  body: {
    backgroundColor: Colors.white,
  },
  sectionContainer: {
    marginTop: 32,
    paddingHorizontal: 24,
  },
  sectionTitle: {
    fontSize: 24,
    fontWeight: '600',
    color: Colors.black,
    textAlign: "center"
  },
  sectionDescription: {
    marginTop: 8,
    fontSize: 18,
    fontWeight: '400',
    color: Colors.dark,
  },
  highlight: {
    fontWeight: '700',
    margin: 2
  },
  footer: {
    color: Colors.dark,
    fontSize: 12,
    fontWeight: '600',
    padding: 4,
    paddingRight: 12,
    textAlign: 'right',
  },
  separator: {
    marginVertical: 8,
    borderBottomColor: '#737373',
    borderBottomWidth: StyleSheet.hairlineWidth,
  },
});

export default App;

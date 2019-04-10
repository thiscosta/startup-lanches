import { takeEvery, call, put } from 'redux-saga/effects'

import {
    CARREGAR_LISTA_LANCHES, carregouListaLanches,
    alterarConteudoProntoLanche
} from '../reducers/lanchesReducer'

import { alertarErro } from '../reducers/mensagemReducer'

import LanchesService from '../services/lanchesService'


//Sagas
//LOADS
function* loadListLanches() {
    try {
        //yield call()
        const result = yield call(LanchesService.listarLanches)
        if (result.success)
            yield put(carregouListaLanches({ listaLanches: result.data }))
        else{
            yield put(alertarErro({ erro: 'Falha ao obter lanches do servidor. Por favor, tente novamente.' }))
            yield put(alterarConteudoProntoLanche())
        }
    }
    catch (erro) {
        yield put(alertarErro({ erro: 'Falha ao obter lanches do servidor. Por favor, tente novamente.' }))
        yield put(alterarConteudoProntoLanche())
    }
}

export const sagasLanches = [
    takeEvery(CARREGAR_LISTA_LANCHES.START, loadListLanches)
]

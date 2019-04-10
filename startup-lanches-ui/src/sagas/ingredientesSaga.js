import { takeEvery, call, put } from 'redux-saga/effects'

import {
    CARREGAR_LISTA_INGREDIENTES, carregouListaIngredientes,
    alterarConteudoProntoIngrediente
} from '../reducers/ingredientesReducer'

import { alertarErro } from '../reducers/mensagemReducer'

import IngredientesService from '../services/ingredientesService'


//Sagas
function* carregarListaIngredientes() {
    try {

        const result = yield call(IngredientesService.listarIngredientes)
        if (result.success)
            yield put(carregouListaIngredientes({ ingredientes: result.data }))
        else {
            yield put(alertarErro({ erro: 'Falha ao obter ingredientes do servidor. Por favor, tente novamente.' }))
            yield put(alterarConteudoProntoIngrediente())
        }
    }
    catch (erro) {
        yield put(alertarErro({ erro: 'Falha ao obter ingredientes do servidor. Por favor, tente novamente.' }))
        yield put(alterarConteudoProntoIngrediente())
    }
}

export const sagasIngredientes = [
    takeEvery(CARREGAR_LISTA_INGREDIENTES.START, carregarListaIngredientes)
]

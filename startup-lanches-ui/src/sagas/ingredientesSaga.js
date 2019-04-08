import { takeEvery,  call, put } from 'redux-saga/effects'

import {
    CARREGAR_LISTA_INGREDIENTES, carregouListaIngredientes,
    alertarErro
} from '../reducers/ingredientesReducer'

import IngredientesService from '../services/ingredientesService'


//Sagas
function* carregarListaIngredientes() {
    try {

        const result = yield call(IngredientesService.listarIngredientes)
        if (Array.isArray(result))
            yield put(carregouListaIngredientes({ ingredientes: result }))
        else
            throw new Error("Falha ao carregar lista de ingredientes")
    }
    catch (erro) {
        yield put(alertarErro({ erro: erro }))
    }
}

export const sagasIngredientes = [
    takeEvery(CARREGAR_LISTA_INGREDIENTES.START, carregarListaIngredientes)
]

import { all } from 'redux-saga/effects'

import { sagasLanches } from '../sagas/lanchesSaga'
import { sagasCompras } from '../sagas/comprasSaga'
import { sagasIngredientes } from '../sagas/ingredientesSaga';

export default function* rootSaga() {
    yield all([
        ...sagasLanches,
        ...sagasCompras,
        ...sagasIngredientes
    ])
}

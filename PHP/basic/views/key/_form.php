<?php

use yii\helpers\Html;
use yii\widgets\ActiveForm;

/* @var $this yii\web\View */
/* @var $model app\models\Key */
/* @var $form yii\widgets\ActiveForm */
?>

<div class="key-form">

    <?php $form = ActiveForm::begin(); ?>

    <?= $form->field($model, 'gentime')->textInput() ?>

    <?= $form->field($model, 'isactive')->checkbox() ?>

    <div class="form-group">
        <?= Html::submitButton($model->isNewRecord ? 'Create' : 'Update', ['class' => $model->isNewRecord ? 'btn btn-success' : 'btn btn-primary']) ?>
    </div>

    <?php ActiveForm::end(); ?>

</div>

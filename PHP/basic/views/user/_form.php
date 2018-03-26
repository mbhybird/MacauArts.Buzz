<?php

use yii\helpers\Html;
use yii\widgets\ActiveForm;

/* @var $this yii\web\View */
/* @var $model app\models\User */
/* @var $form yii\widgets\ActiveForm */
?>

<div class="user-form">

    <?php $form = ActiveForm::begin(); ?>

    <?= $form->field($model, 'userid')->textInput(['maxlength' => 20]) ?>

    <?= $form->field($model, 'usernamecn')->textInput(['maxlength' => 20]) ?>

    <?= $form->field($model, 'usernameen')->textInput(['maxlength' => 20]) ?>

    <?= $form->field($model, 'isauthorized')->textInput() ?>

    <?= $form->field($model, 'defaultlang')->textInput() ?>

    <div class="form-group">
        <?= Html::submitButton($model->isNewRecord ? 'Create' : 'Update', ['class' => $model->isNewRecord ? 'btn btn-success' : 'btn btn-primary']) ?>
    </div>

    <?php ActiveForm::end(); ?>

</div>

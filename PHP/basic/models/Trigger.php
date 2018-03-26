<?php

namespace app\models;

use Yii;

/**
 * This is the model class for table "trigger".
 *
 * @property integer $id
 * @property integer $triggertype
 * @property integer $triggercount
 * @property integer $triggerfrequency
 */
class Trigger extends \yii\db\ActiveRecord
{
    /**
     * @inheritdoc
     */
    public static function tableName()
    {
        return 'trigger';
    }

    /**
     * @inheritdoc
     */
    public function rules()
    {
        return [
            [['triggertype', 'triggercount', 'triggerfrequency'], 'integer']
        ];
    }

    /**
     * @inheritdoc
     */
    public function attributeLabels()
    {
        return [
            'id' => 'ID',
            'triggertype' => 'Triggertype',
            'triggercount' => 'Triggercount',
            'triggerfrequency' => 'Triggerfrequency',
        ];
    }
}

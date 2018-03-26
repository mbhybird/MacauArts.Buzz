<?php

namespace app\models;

use Yii;

/**
 * This is the model class for table "sysparams".
 *
 * @property integer $id
 * @property string $paramgroup
 * @property integer $paramkey
 * @property string $paramvalue
 */
class Sysparams extends \yii\db\ActiveRecord
{
    /**
     * @inheritdoc
     */
    public static function tableName()
    {
        return 'sysparams';
    }

    /**
     * @inheritdoc
     */
    public function rules()
    {
        return [
            [['paramkey'], 'integer'],
            [['paramgroup', 'paramvalue'], 'string', 'max' => 20]
        ];
    }

    /**
     * @inheritdoc
     */
    public function attributeLabels()
    {
        return [
            'id' => 'ID',
            'paramgroup' => 'Paramgroup',
            'paramkey' => 'Paramkey',
            'paramvalue' => 'Paramvalue',
        ];
    }
}
